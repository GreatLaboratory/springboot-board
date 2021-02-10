package com.greatlaboratory.board.domain.user;

import com.greatlaboratory.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    // jpa로 데이터베이스에 저장할 때 enum값을 어떤 타입으로 저장할지 결정
    // spring securtiy에선 항상 권한 코드에 ROLE_이 앞에 있어야 한다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 db에 저장하기 위한 기능
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
