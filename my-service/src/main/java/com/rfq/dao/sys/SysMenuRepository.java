package com.rfq.dao.sys;

import com.rfq.dao.BaseRepository;
import com.rfq.entity.sys.SysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SysMenuRepository extends BaseRepository<SysMenu, String> {


    @Query(nativeQuery=true,value = "Select * from sysmenu where " +
            " CHARINDEX(CONVERT(varchar(20),isnull(copyId,'0000')),:copys,0)>0\n" +
//            "and  not copyId = 0 \n"+
            "order by orderId")
    List<SysMenu> queryMenus(@Param("copys") String copys);
}