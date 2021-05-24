package com.rfq.dao.sys;

import com.rfq.dao.BaseRepository;
import com.rfq.entity.sys.SysRoler;

import java.util.Optional;


public interface SysRolerRepository extends BaseRepository<SysRoler, String> {

    Optional<SysRoler> findByLoginIdAndStatus(String loginId, String cancelStateUncencel);
}
