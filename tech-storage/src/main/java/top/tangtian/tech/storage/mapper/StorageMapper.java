package top.tangtian.tech.storage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;


/**
 * @author tangtian
 * @date 2025-12-31 19:28
 */
@Mapper
@Repository
public interface StorageMapper {

	@Select("SELECT \"count\" FROM storage WHERE commodity_code = #{commodityCode}")
	Integer getStock(@Param("commodityCode") String commodityCode);

	@Update("UPDATE storage SET count = count - #{count},update_time=#{updateTime} WHERE commodity_code = #{commodityCode}")
	int reduceStock(@Param("commodityCode") String commodityCode,
					@Param("count") Integer count, @Param("updateTime") Timestamp updateTime);

}
