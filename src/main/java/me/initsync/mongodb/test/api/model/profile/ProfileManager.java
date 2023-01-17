package me.initsync.mongodb.test.api.model.profile;

import java.util.UUID;

public interface ProfileManager {
	/**
	 * Creates a new profile for the user.
	 *
	 * @param uuid UUID of user.
	 * @param name Nickname of user.
	 */
	void createProfile(UUID uuid, String name);
	
	/**
	 * Returns the profile using the uuid specified.
	 *
	 * @param uuid UUID of user.
	 * @return A Profile object. Could return null if the profile doesn't exist.
	 */
	Profile getProfile(UUID uuid);
}
