package top.tangtian.tech.consumer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author tangtian
 * @date 2025-12-31 17:30
 */
@Mapper
@Repository
public interface PraiseMapper {

	@Update("update item set praise = praise+1,update_time=#{updateTime} where id = #{itemId}")
	int praiseItem(@Param("itemId") Integer itemId,
				   @Param("updateTime") Timestamp updateTime);

	@Select("select praise from item where id = #{itemId}")
	int getPraise(@Param("itemId") Integer itemId);

}
