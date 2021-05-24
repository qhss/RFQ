package com.hotel.dao.sys;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.Organization;

public interface OrganizationRepository extends BaseRepository<Organization, String> {

    /**
     * 获得根节点
     * @param companyId
     * @param Deleted
     * @param pId
     * @return
     */
    Optional<Organization> findByCompanyIdAndDeletedAndPid(String companyId, Integer Deleted, String pId);

    /**
     * 根据父级Id获得子节点
     * @param pId
     * @param Deleted
     * @return
     */
/*    @Query(value="select o.sort as sort,o.deleted as deleted,o.companyId as companyId,o.createTime as createTime, " +
            "o.name as name,o.id as id,o.type as type,o.canceled as canceled,o.memo as memo," +
            "(select count(ai.id) from AreaInfo ai where ai.warehouseId=o.id and ai.deleted=0) as childCount " +
            "from Organization o  "+
            "where o.companyId=:companyId and o.pid=:pId and o.deleted=0 ")
    List<Map<String,Object>> queryByPidAndDeleted(String companyId, String pId);*/

    /**
     * 根据父级Id获得子节点
     * @param pId
     * @param companyId
     * @return
     */
    @Query(nativeQuery = true,value="SELECT * FROM (SELECT bai.`id` AS id,bai.name AS `name`,bai.`warehouse_id` AS pid, 0 AS childCount," +
            "bai.create_time as createTime,-1 AS `type`,2 as iconMark,bai.code as code FROM  bs_area_info bai " +
            "JOIN sys_organization o  ON bai.`warehouse_id`=o.`id`\n" +
            "WHERE bai.`deleted`=0 and o.company_id=:companyId " +
            "UNION ALL\n" +
            "SELECT o.id AS id,o.`name` AS `name`,o.`pid` AS pid,\n" +
            "(SELECT COUNT(1) FROM bs_area_info bai2 WHERE bai2.`deleted`=0 AND bai2.`warehouse_id`=o.`id`) AS childCount," +
            " o.create_time as createTime,2 AS `type`,1 as iconMark,o.code as code FROM sys_organization o " +
            "WHERE o.`pid`=:pId AND o.`deleted`=0 and o.company_id=:companyId and o.type = 2 ) AS nodeTable WHERE pid =:pId  order by code asc")
    List<Map<String,Object>> queryChildeNode(String companyId, String pId);



    /**
     * 获得所有仓库
     * @param deleted
     * @param type
     * @param companyId
     * @return
     */
    Optional<List<Organization>> findByDeletedAndTypeAndCompanyId(Integer deleted,Integer type,String companyId);
    
    /**
     * 根据code查找公司内所有仓库
     * @param companyId
     * @param code
     * @param type
     * @return
     */
    @Query("select id as warehouseId,name as warehouseName "
    		+ "from Organization "
    		+ "where companyId = :companyId and deleted = 0 and code = :code and type = :type")
    List<Map<String,Object>> queryByCompanyIdAndCodeAndType(@Param("companyId") String companyId,@Param("code") String code,@Param("type") int type);

    /**
     * 获得节点下的子节点
     * @param pid
     * @param companyId
     * @return
     */
    @Query(value = "select o.pid as pid,o.code as code,o.code as code,o.sort as sort,o.deleted as deleted,o.companyId as companyId,o.createTime as createTime, " +
            "o.name as name,o.id as id,o.type as type,o.canceled as canceled,o.memo as memo,(select count(o2.id) from Organization o2 where o2.pid=o.id) as childCount " +
            "from Organization o where o.pid=:pid and o.deleted=0 and o.companyId=:companyId " +
            "order by o.sort desc ")
    List<Map<String,Object>> getNodesByPid(String pid,String companyId);
    
    
    /**
     * 根据仓库编码在公司内查找所有符合条件的仓库id
     * @param companyId
     * @param warehouseTypes
     * @return
     */
    @Query(nativeQuery=true,value="select group_concat(id separator ',') "
    		+ "from sys_organization "
    		+ "where company_id = :companyId and code = :code and type = :type and deleted = 0 "
    		+ "group by code")
    Optional<String> queryByCompanyIdAndCodeAndTypeAndDeleted(@Param("companyId") String companyId,@Param("code") String code,@Param("type") int type);


}
