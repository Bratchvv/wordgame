package com.wordgame.statistics.service;

import com.wordgame.gameplay.repository.PlayerRepository;
import com.wordgame.statistics.dto.EditableRatingTableDataDto;
import com.wordgame.statistics.dto.EditableRatingTableDto;
import com.wordgame.statistics.dto.RatingPlayerListDataDto;
import com.wordgame.statistics.dto.RatingTableDataDto;
import com.wordgame.statistics.dto.RatingTableDto;
import com.wordgame.statistics.dto.RatingTableListDataDto;
import com.wordgame.statistics.dto.RatingTopDto;
import com.wordgame.statistics.dto.RatingTopDto.Range;
import com.wordgame.statistics.dto.RatingTopDto.Res;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import com.wordgame.statistics.repository.RatingDataListRepository;
import com.wordgame.statistics.repository.RatingTableDataRepository;
import com.wordgame.statistics.repository.RatingTableListRepository;
import com.wordgame.statistics.repository.RatingTableRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingTablesService {

    private final ModelMapper statisticsModelMapper;
    private final RatingTableRepository ratingTableRepository;
    private final RatingTableListRepository ratingTableListRepository;
    private final PlayerRepository playerRepository;
    private final RatingTableDataRepository ratingTableDataRepository;
    private final RatingDataListRepository ratingDataListRepository;

    public EditableRatingTableDto createRatingTable(String name, Integer expireHoursCycle) {
        var ratingTable = ratingTableRepository.findFirstByName(name);
        var isNew = false;
        if (ratingTable == null) {
            ratingTable = new RatingTable();
            ratingTable.setName(name);
            isNew = true;
        }
        ratingTable.setExpireHoursCycle(expireHoursCycle);
        ratingTable.setInitTimeUtc(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
        ratingTableRepository.save(ratingTable);
        var dto = statisticsModelMapper.map(ratingTable, EditableRatingTableDto.class);
        dto.setNew(isNew);
        return dto;
    }

    public EditableRatingTableDataDto insertDataToTable(String name, String playerId, Integer value) {
        var ratingTableData = ratingTableDataRepository
            .findFirstByNameAndPlayer_Id(name, playerId);
        var isNew = false;
        if (ratingTableData == null) {
            var ratingTable = ratingTableRepository.findFirstByName(name);
            ratingTableData = new RatingTableData();
            ratingTableData.setName(ratingTable.getName());
            ratingTableData.setRatingTable(ratingTable);
            ratingTableData.setPlayer(playerRepository.findById(playerId)
                                          .orElseThrow(EntityNotFoundException::new));
            ratingTableData.setValue(value);
            ratingTableDataRepository.save(ratingTableData);
            isNew = true;
        } else {
            ratingTableData.setValue(value);
            ratingTableDataRepository.save(ratingTableData);
        }
        var dto = statisticsModelMapper
            .map(ratingTableData, EditableRatingTableDataDto.class);
        dto.setNew(isNew);
        return dto;
    }

    public RatingTableDataDto getPlayerRatingInTable(String playerId, String name) {
        var ratingTableData = ratingTableDataRepository
            .findFirstByNameAndPlayer_Id(name, playerId);
        return statisticsModelMapper.map(ratingTableData, RatingTableDataDto.class);
    }

    public Page<RatingTableListDataDto> getRatingsByTable(String name, Pageable pageable) {
        return ratingDataListRepository.findAllByName(name, pageable)
            .map(v -> statisticsModelMapper.map(v, RatingTableListDataDto.class));
    }

    public Page<RatingTableDto> getRatingsTableList(Pageable pageable) {
        return ratingTableListRepository.findAll(pageable)
            .map(v -> statisticsModelMapper.map(v, RatingTableDto.class));
    }

    public Page<RatingPlayerListDataDto> getRatingsByPlayer(String playerId, Pageable pageable) {
        return ratingDataListRepository.findAllByPlayer_Id(playerId, pageable)
            .map(v -> statisticsModelMapper.map(v, RatingPlayerListDataDto.class));
    }

    public RatingTopDto getPlayerRatingsData(String id, String name, Integer upperSize,
                                             Integer topSize, Integer lowerSize) {
        return RatingTopDto.builder()
            .name(name)
            .res(List.of(Res.builder().id(id).place(1).name("qedw").urlAvatar("aefwe").value(123).build()))
            .range(List.of(Range.builder().start(0).size(2).build()))
            .build();
    }

    public void clearRatings() {
        ratingTableRepository.findAll().forEach(ratingTable -> {
            var cycle = ratingTable.getExpireHoursCycle();
            var startTime = ratingTable.getInitTimeUtc();
            var millis = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
            if (millis >= (startTime + cycle * 60 * 60 * 1000)) {
                clearRating(ratingTable.getId());
            }
        });
    }

    @Transactional
    public void clearRating(Long id) {
        ratingTableDataRepository.deleteAllByRatingTable_Id(id);
    }

    @Transactional
    public void updateHours(Long id, Integer hours) {
        var table = ratingTableRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        table.setExpireHoursCycle(hours);
        table.setInitTimeUtc(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
        ratingTableRepository.save(table);
    }
}
