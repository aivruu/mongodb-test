package me.initsync.mongodb.test.model;

import com.google.common.base.Preconditions;
import me.initsync.mongodb.test.api.model.profile.Profile;
import me.initsync.mongodb.test.api.model.profile.ProfileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleProfileManager implements ProfileManager {
	private final Map<UUID, Profile> profiles;
	
	public SimpleProfileManager() {
		profiles = new HashMap<>();
	}
	
	@Override
	public void createProfile(UUID uuid, String name) {
		Preconditions.checkNotNull(uuid, "The uuid is null.");
		Preconditions.checkNotNull(name, "The user name is null.");
		
		if (profiles.containsKey(uuid)) return;
		
		profiles.put(uuid, new Profile(uuid, name));
	}
	
	@Override
	public Profile getProfile(UUID uuid) {
		Preconditions.checkNotNull(uuid, "The uuid is null.");
		
		return profiles.getOrDefault(uuid, null);
	}
}
