package com.lz.snappy.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
//使用此注解使get和set方法的返回值为对象
@Accessors(chain=true)
public class UserContent {

	private String userName;
	private String pwd;
}
