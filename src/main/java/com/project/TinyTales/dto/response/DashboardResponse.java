package com.project.TinyTales.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private Long categoryCount;
    private Long storiesCount;
    //private Long readersCount;
}
