package com.rams.repository;

import com.rams.entity.Resource;
import com.rams.entity.ResourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    // Derived query — Spring Data writes the SQL for you from the method name.
    List<Resource> findByStatus(ResourceStatus status);
    List<Resource> findByTypeAndStatus(String type, ResourceStatus status);
    long countByStatus(ResourceStatus status);
}
