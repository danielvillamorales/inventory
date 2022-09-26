package com.company.inventory.inventory.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReponseRest extends ResponseRest {
    private CategoryResponse categoryResponse = new CategoryResponse();
}
