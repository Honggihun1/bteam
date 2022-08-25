package com.example.project01.DTO;
// 이름만 IdsDTO 이지 저장되는 값은 count (중복체크시 검색되는 아이디 개수)임
public class IdsDTO {

    private String id;

    public IdsDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
