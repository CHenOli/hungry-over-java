package com.tcc.carloshenrique.hungryover.models;
import com.squareup.moshi.Json;

public class UserModel {
    public class Example {

        @Json(name = "id")
        private long id;
        @Json(name = "nome")
        private String nome;
        @Json(name = "email")
        private String email;
        @Json(name = "senha")
        private String senha;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Example withId(long id) {
            this.id = id;
            return this;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Example withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Example withEmail(String email) {
            this.email = email;
            return this;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public Example withSenha(String senha) {
            this.senha = senha;
            return this;
        }
    }
}
