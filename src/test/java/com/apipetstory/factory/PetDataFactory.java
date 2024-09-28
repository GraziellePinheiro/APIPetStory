package com.apipetstory.factory;

import java.util.List;

import com.apipetstory.pojo.Category;
import com.apipetstory.pojo.PetPojo;
import com.apipetstory.pojo.Tag;

public class PetDataFactory {
    public static PetPojo criarPetPojo(){

        PetPojo newPet = new PetPojo();

        newPet.setId(1);

        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        newPet.setCategory(category);

        newPet.setName("Rex");

        newPet.setPhotoUrls(List.of(" "));

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Friendly");
        newPet.setTags(List.of(tag));

        newPet.setStatus("available");

        return newPet;
    }
}
