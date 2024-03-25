package kr.mjc.jacob.spring.springdata.user

import kr.mjc.jacob.spring.springdata.applicationContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.*

fun main() {
  val userRepository = applicationContext.getBean(UserRepository::class.java)

  print("List - pageNumber pageSize ? ")
  val scanner = Scanner(System.`in`)
  val pageable = PageRequest.of(scanner.nextInt(), scanner.nextInt(),
      Sort.by(Sort.Direction.DESC, "id"))
  scanner.close()

  val userList = userRepository.findAll(pageable)
  userList.forEach(::println)
}