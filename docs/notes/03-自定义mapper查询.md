# 自定义mapper查询

```java
@Mapper
public interface FriendshipMapper extends BaseMapper<Friendship> {

    List<FriendShipVO> queryMyFriends(
            @Param("userId") Long userId,
            @Param("isBlocked")Boolean isBlocked
    );
}
```
sdfsaf

```java
<select id="queryMyFriends" resultType="com.wangkee.vo.FriendShipBriefVO">
    select
        fr.id as friendShipId,
        fr.user_id as userId,
        fr.friend_id as friendId,
        u.nickname as friendNickname,
        u.avatar as friendAvatar,
        fr.remark as remark,
        fr.chatonly as chatOnly,
        fr.blocked as blocked
    from
        follow as fr
    left join
        user as u
    on
        fr.user_id = u.id
    where
        fr.user_id = #{userId}
    and
        <if test="!isBlocked">
            fr.blocked = 0
        </if>

        <if test="isBlocked">
            fr.blocked = 1
        </if>
    order by
        u.nickname
</select>
```