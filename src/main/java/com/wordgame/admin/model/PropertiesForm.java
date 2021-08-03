package com.wordgame.admin.model;

import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.dto.GameWordsInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vladimir Bratchikov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesForm {

    private List<GamePropertyDto> properties;
    private GameWordsInfoDto gameWordsInfo;
}
