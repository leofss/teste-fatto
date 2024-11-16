package com.leonardo.task.dto;

import com.leonardo.task.enumerator.MoveType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveDto {
    @Enumerated(EnumType.STRING)
    private MoveType moveType;
}
