package br.com.consep.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.consep.api.dashboard.dto.ProjectDashboardDto;
import br.com.consep.api.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query("""
            SELECT new br.com.consep.api.dashboard.dto.ProjectDashboardDto(
                    COUNT(p),
                    SUM(
                        CASE
                            WHEN p.projectStatus = br.com.consep.api.shared.enums.ProjectStatus.COMPLETED
                            THEN 1
                            ELSE 0
                        END
                    ),
                    SUM(
                        CASE
                            WHEN p.projectStatus = br.com.consep.api.shared.enums.ProjectStatus.ONGOING
                            THEN 1
                            ELSE 0
                        END
                    ),
                    COALESCE(SUM(p.value), 0)
                )
                FROM Project p
                    """)
    ProjectDashboardDto findProjectDashboard();

    int countProjectByOrganizationId(Long id);

    void deleteByOrganizationId(Long organizationId);
}
