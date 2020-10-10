package me.hoseok.ppmtool.repository;

import me.hoseok.ppmtool.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String toUpperCase);
}
