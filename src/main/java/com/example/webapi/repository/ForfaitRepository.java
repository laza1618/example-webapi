package com.example.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webapi.model.Forfait;

@Repository
public interface ForfaitRepository extends JpaRepository<Forfait, Long> {

}
