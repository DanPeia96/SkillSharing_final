package es.uji.ei1027.SkillSharing;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SkillSharingApplication{
	private static final Logger log = Logger.getLogger(SkillSharingApplication .class.getName());
	public static void main(String[] args) {
		new SpringApplicationBuilder(SkillSharingApplication.class).run(args);
	}
}
