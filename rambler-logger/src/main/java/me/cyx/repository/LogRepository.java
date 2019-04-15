package me.cyx.repository;

import org.springframework.stereotype.Repository;

/**
 * @author jie
 * @date 2018-11-24
 */
@Repository
public interface LogRepository  {

    /**
     * 获取一个时间段的IP记录
     * @param date1
     * @param date2
     * @return
     */
//    @Query(value = "select count(*) FROM (select request_ip FROM log where create_time between ?1 and ?2 GROUP BY request_ip) as s",nativeQuery = true)
    Long findIp(String date1, String date2);
}
