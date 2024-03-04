package kr.mjc.jacob.springjdbc.user

import kr.mjc.jacob.jdbc.Page
import kr.mjc.jacob.jdbc.user.User
import kr.mjc.jacob.jdbc.user.usinghelper.UserRepository
import kr.mjc.jacob.map
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val nt: NamedParameterJdbcTemplate) :
    UserRepository {

  private val FIND_ALL =
    "select id, username, password, first_name, date_joined from user order by id desc limit :offset, :size"

  private val FIND_BY_ID =
    "select id, username, password, first_name, date_joined from user where id=:id"

  private val FIND_BY_USERNAME =
    "select id, username, password, first_name, date_joined from user where id=:username"

  private val SAVE =
    "insert user(username, password, first_name) values(:username, :password, :firstName) returning *"

  private val CHANGE_PASSWORD =
    "update user set password=:password where id=:id"

  private val DELETE_BY_ID = "delete from user where id=:id"

  private val userRowMapper = BeanPropertyRowMapper(User::class.java)

  override fun findAll(page: Page): List<User> =
    nt.query(FIND_ALL, page.map, userRowMapper)

  override fun findById(id: Int): User? =
    nt.queryForObject(FIND_BY_ID, mapOf("id" to id), userRowMapper)

  override fun findByUsername(username: String): User? =
    nt.queryForObject(FIND_BY_USERNAME, mapOf("username" to username),
        userRowMapper)

  override fun save(user: User): User? =
    nt.queryForObject(SAVE, user.map, userRowMapper)

  override fun changePassword(id: Int, password: String) {
    nt.update(CHANGE_PASSWORD, mapOf("id" to id, "password" to password))
  }

  override fun deleteById(id: Int) {
    nt.update(DELETE_BY_ID, mapOf("id" to id))
  }
}