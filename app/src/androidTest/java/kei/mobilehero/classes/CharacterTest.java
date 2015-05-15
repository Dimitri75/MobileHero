package kei.mobilehero.classes;

import android.test.InstrumentationTestCase;

import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class CharacterTest extends InstrumentationTestCase{
    private final static String CHARAC_NAME = "Character";
    private final static String CHARAC_GENDER="Male";
    private final static String CHARAC_ALIGNMENT="Evil";
    private final static String CHARAC_RACE="Human";
    private final static String CHARAC_CLASSNAME="Warlock";
    private final static String CHARAC_PICTURE="www.mobilehero.fr/images/hero.png";

    private final static String SKILL_NAME = "Skill";
    private final static String SKILL_DESCRIPTION = "This is a skill.";
    private final static double SKILL_VALUE = 4.2;

    private final static String CARAC_NAME = "Caracteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    private final static String EQUIP_NAME = "Equipment";
    private final static String EQUIP_DESCRIPTION = "This is an equipment.";
    private final static double EQUIP_WEIGHT = 10.5;
    private final static String EQUIP_POSITION = "Head";

    private final static String EFFECT_NAME = "Effect";
    private final static String EFFECT_DESCRIPTION = "This is an effect.";
    private final static double EFFECT_VALUE = 4.2;

    public void test() throws Exception {
        Skill skill = new Skill(SKILL_NAME, SKILL_DESCRIPTION, SKILL_VALUE);
        Caracteristic caracteristic = new Caracteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);

        kei.mobilehero.classes.general.Character character = new Character(CHARAC_NAME);
        character.setGender(CHARAC_GENDER);
        character.setAlignment(CHARAC_ALIGNMENT);
        character.setRace(CHARAC_RACE);
        character.setClassName(CHARAC_CLASSNAME);
        character.setPicture(CHARAC_PICTURE);
        character.getSkills().add(skill);
        character.getCaracteristics().add(caracteristic);
        character.getEquipments().add(equipment);

        assertEquals(character.getName(), CHARAC_NAME);
        assertEquals(character.getGender(), CHARAC_GENDER);
        assertEquals(character.getAlignment(), CHARAC_ALIGNMENT);
        assertEquals(character.getRace(), CHARAC_RACE);
        assertEquals(character.getClassName(), CHARAC_CLASSNAME);
        assertEquals(character.getPicture(), CHARAC_PICTURE);
        assertEquals(character.getSkills().get(0), skill);
        assertEquals(character.getCaracteristics().get(0), caracteristic);
        assertEquals(character.getEquipments().get(0), equipment);
    }

    public void testEquipmentWeight() throws Exception {
        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);

        Character character = new Character(CHARAC_NAME);
        character.getEquipments().add(equipment);
        character.getEquipments().add(equipment);

        assertEquals(character.getEquipmentWeight(), EQUIP_WEIGHT*2);
    }

    public void testCalculatedCaracteristics() throws Exception {
        Caracteristic caracteristic = new Caracteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Effect effect = new Effect(EFFECT_NAME, EFFECT_DESCRIPTION, EFFECT_VALUE, caracteristic);

        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);
        equipment.getEffects().add(effect);

        Character character = new Character(CHARAC_NAME);
        character.getCaracteristics().add(caracteristic);
        character.getEquipments().add(equipment);

        assertEquals(character.getCalculatedCaracteristics().get(CARAC_NAME), CARAC_VALUE + EFFECT_VALUE);
    }
}
