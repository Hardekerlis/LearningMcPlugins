package se.gustaf.learning.rpg;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mineacademy.fo.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassRegister {
	
	@Getter
	private static final ClassRegister instance = new ClassRegister();
	
	@Getter
	private final List<PlayerClass> loadedClasses = new ArrayList<>();
	
	public void createClass(final String name) {
		final PlayerClass playerClass = new PlayerClass(name);
		playerClass.save();
		
		loadedClasses.add(playerClass);
	}
	
	public void loadClasses() {
		loadedClasses.clear();
		
		for (final File file : FileUtil.getFiles("classes", "yml")) {
			final PlayerClass playerClass = new PlayerClass(file.getName());
			
			loadedClasses.add(playerClass);
		}
	}
	
}
