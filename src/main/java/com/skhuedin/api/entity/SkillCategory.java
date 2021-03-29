package com.skhuedin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkillCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "skill_category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Builder
    public SkillCategory(String name, Talent talent) {

        Assert.hasText(name, "이름 값은 필수입니다. ");
        Assert.hasText(String.valueOf(talent), "talent 값은 필수입니다. ");
        this.name = name;
        this.talent = talent;
    }
}