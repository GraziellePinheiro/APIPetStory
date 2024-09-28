package com.apipetstory.factory;

import com.apipetstory.pojo.UsuarioPojo;

public class UsuarioDataFactory {
    public static UsuarioPojo criarUsuarioPojo(){

        UsuarioPojo usuario = new UsuarioPojo();
        
        usuario.setId(1);
        usuario.setUsername("user1");
        usuario.setFirstName("user1");
        usuario.setLastName("user1");
        usuario.setEmail("user1@email.com");
        usuario.setPassword("user1");
        usuario.setPhone("9999-9999");
        usuario.setUserStatus(1);

        return usuario;
    }
}
