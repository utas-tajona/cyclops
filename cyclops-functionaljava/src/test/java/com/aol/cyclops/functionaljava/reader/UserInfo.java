package com.aol.cyclops.functionaljava.reader;

import java.util.HashMap;
import java.util.Map;

import com.aol.cyclops.comprehensions.donotation.typed.Do;
import com.aol.cyclops.functionaljava.FJ;
import com.aol.cyclops.lambda.monads.AnyM;

import fj.data.Reader;

public class UserInfo implements Users {

	public Reader<UserRepository,Map> userInfo(String username) {
			
		return	FJ.unwrapReader(
				Do.add(FJ.anyM(this.findUser(username)))
					.withAnyM(user -> FJ.anyM(this.getUser(user.getSupervisor().getId())))
					.yield(user -> boss -> buildMap(user,boss))
					);
	}

	private Map buildMap(User user, User boss) {
		return new HashMap(){{
				put("fullname",user.getName());
				put("email",user.getEmail());
				put("boss",boss.getName());
				
		}};
	}
}
