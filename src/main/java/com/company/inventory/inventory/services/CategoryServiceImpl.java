package com.company.inventory.inventory.services;

import com.company.inventory.inventory.model.repository.CategoryDao;
import com.company.inventory.inventory.response.CategoryReponseRest;
import com.company.inventory.inventory.web.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    private final ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryReponseRest> search() {
        CategoryReponseRest response = new CategoryReponseRest();
        try {
            List<CategoryDto> categorias = categoryDao.findAll().stream()
                    .map(categoria -> modelMapper.map(categoria,CategoryDto.class))
                    .collect(Collectors.toList());
            response.getCategoryResponse().setCategoryDto(categorias);
            response.setMetadata("respuesta ok","oo","Respuesta Exitosa");
        }catch (Exception e){
            response.setMetadata("respuesta nok","-1","error al consultar");
            e.getStackTrace();
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
