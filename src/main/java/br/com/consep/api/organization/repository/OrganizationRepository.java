package br.com.consep.api.organization.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.consep.api.dashboard.dto.OrganizationDashboardDto;
import br.com.consep.api.organization.entity.Organization;

@Repository
public interface OrganizationRepository
        extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    @Query("""
                SELECT new br.com.consep.api.dashboard.dto.OrganizationDashboardDto(
                    o.id,
                    o.name,
                    COUNT(DISTINCT u.id),
                    COUNT(DISTINCT p.id),
                    COUNT(DISTINCT pn.id)
                )
                FROM Organization o
                LEFT JOIN User u ON u.organization = o
                LEFT JOIN Project p ON p.organization = o
                LEFT JOIN PublicNotice pn ON pn.organization = o
                GROUP BY o.id, o.name
            """)
    List<OrganizationDashboardDto> findOrganizationDashboard(Pageable pageable);
}
