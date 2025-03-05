package com.bovo.Bovo.modules.archive.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBooksResponse {
    private List<MyBooksDto> myBooksList;
}

