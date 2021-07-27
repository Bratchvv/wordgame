package com.wordgame.admin.model;

import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.dto.GameWordsInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesForm {
    private List<GamePropertyDto> properties;
    private GameWordsInfoDto gameWordsInfo;
}
