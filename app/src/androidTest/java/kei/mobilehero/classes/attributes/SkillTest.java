package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class SkillTest extends InstrumentationTestCase{
    private final static String SKILL_NAME = "Skill";
    private final static String SKILL_DESCRIPTION = "This is a skill.";
    private final static double SKILL_VALUE = 4.2;

    public void test() throws Exception {
        Skill skill = new Skill(SKILL_NAME, SKILL_DESCRIPTION, SKILL_VALUE);

        assertEquals(skill.getName(), SKILL_NAME);
        assertEquals(skill.getDescription(), SKILL_DESCRIPTION);
        assertEquals(skill.getValue(), SKILL_VALUE);
    }
}
