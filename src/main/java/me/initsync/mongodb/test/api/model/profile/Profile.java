package me.initsync.mongodb.test.api.model.profile;

import me.initsync.mongodb.test.api.model.UserModel;

import java.util.UUID;

public class Profile {
	private final UserModel user;
	
	public Profile(UUID uuid, String name) {
		user = new UserModel(uuid, name);
	}
	
	/**
	 * Returns the UserModel object of user.
	 *
	 * @return A UserModel object.
	 */
	public UserModel getUser() {
		return user;
	}
}
