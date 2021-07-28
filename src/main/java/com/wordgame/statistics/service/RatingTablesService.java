package com.wordgame.statistics.service;

import com.wordgame.statistics.dto.*;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import com.wordgame.statistics.repository.RatingDataListRepository;
import com.wordgame.statistics.repository.RatingTableDataRepository;
import com.wordgame.statistics.repository.RatingTableRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingTablesService {

    private final ModelMapper statisticsModelMapper;
    private final RatingTableRepository ratingTableRepository;
    private final RatingTableDataRepository ratingTableDataRepository;
    private final RatingDataListRepository ratingDataListRepository;

    public EditableRatingTableDto createRatingTable(String name) {
        var ratingTable = ratingTableRepository.findFirstByName(name);
        var isNew = false;
        if(ratingTable == null) {
            ratingTable = new RatingTable();
            ratingTable.setName(name);
            ratingTableRepository.save(ratingTable);
            isNew = true;
        }
        var dto = statisticsModelMapper.map(ratingTable, EditableRatingTableDto.class);
        dto.setNew(isNew);
        return dto;
    }

    public EditableRatingTableDataDto insertDataToTable(String name, Long playerId, Integer value) {
        var ratingTableData = ratingTableDataRepository.findFirstByNameAndPlayerId(name, playerId);
        var isNew = false;
        if(ratingTableData == null) {
            var ratingTable = ratingTableRepository.findFirstByName(name);
            ratingTableData = new RatingTableData();
            ratingTableData.setName(ratingTable.getName());
            ratingTableData.setRatingTable(ratingTable);
            ratingTableData.setPlayerId(playerId);
            ratingTableData.setValue(value);
            ratingTableDataRepository.save(ratingTableData);
            isNew = true;
        } else {
            ratingTableData.setValue(value);
            ratingTableDataRepository.save(ratingTableData);
        }
        var dto = statisticsModelMapper.map(ratingTableData, EditableRatingTableDataDto.class);
        dto.setNew(isNew);
        return dto;
    }

    public RatingTableDataDto getPlayerRatingInTable(Long playerId, String name) {
        var ratingTableData = ratingTableDataRepository.findFirstByNameAndPlayerId(name, playerId);
        return statisticsModelMapper.map(ratingTableData, RatingTableDataDto.class);
    }

    public List<RatingTableListDataDto> getRatingsByTable(String name, Pageable pageable) {
        var ratingTableData = ratingDataListRepository.findAllByName(name, pageable);
        return ratingTableData.stream()
                .map(v -> statisticsModelMapper.map(v, RatingTableListDataDto.class))
                .collect(Collectors.toList());
    }

    public List<RatingPlayerListDataDto> getRatingsByPlayer(Long playerId, Pageable pageable) {
        var ratingTableData = ratingDataListRepository.findAllByPlayerId(playerId, pageable);
        return ratingTableData.stream()
                .map(v -> statisticsModelMapper.map(v, RatingPlayerListDataDto.class))
                .collect(Collectors.toList());
    }
}
