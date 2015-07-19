package kei.mobilehero.classes.general;

import junit.framework.TestCase;

import kei.mobilehero.classes.attributes.Characteristic;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class CharacterTest extends TestCase{
    private final static String CHARAC_NAME = "Character";
    private final static String CHARAC_GENDER="Male";
    private final static String CHARAC_ALIGNMENT="Evil";
    private final static String CHARAC_RACE="Human";
    private final static String CHARAC_CLASSNAME="Warlock";
    private final static String CHARAC_PICTURE="www.mobilehero.fr/images/hero.png";

    private final static String SKILL_NAME = "Skill";
    private final static String SKILL_DESCRIPTION = "This is a skill.";
    private final static double SKILL_VALUE = 4.2;

    private final static String CARAC_NAME = "Characteristic";
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
        Characteristic characteristic = new Characteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);

        kei.mobilehero.classes.general.Character character = new Character(CHARAC_NAME);
        character.setGender(CHARAC_GENDER);
        character.setAlignment(CHARAC_ALIGNMENT);
        character.setRace(CHARAC_RACE);
        character.setClassName(CHARAC_CLASSNAME);
        character.setPicture(CHARAC_PICTURE);
        character.getSkills().put(skill.getName(), skill);
        character.getCharacteristics().put(characteristic.getName(), characteristic);
        character.getEquipments().put(equipment.getName(), equipment);

        assertEquals(character.getName(), CHARAC_NAME);
        assertEquals(character.getGender(), CHARAC_GENDER);
        assertEquals(character.getAlignment(), CHARAC_ALIGNMENT);
        assertEquals(character.getRace(), CHARAC_RACE);
        assertEquals(character.getClassName(), CHARAC_CLASSNAME);
        assertEquals(character.getPicture(), CHARAC_PICTURE);
        assertEquals(character.getSkills().get(0), skill);
        assertEquals(character.getCharacteristics().get(0), characteristic);
        assertEquals(character.getEquipments().get(0), equipment);
    }

    public void testEquipmentWeight() throws Exception {
        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);

        Character character = new Character(CHARAC_NAME);
        character.getEquipments().put(equipment.getName(), equipment);
        character.getEquipments().put(equipment.getName()+"2", equipment);

        assertEquals(character.getEquipmentWeight(), EQUIP_WEIGHT*2);
    }

    public void testCalculatedCaracteristics() throws Exception {
        Characteristic characteristic = new Characteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Effect effect = new Effect(EFFECT_NAME, EFFECT_DESCRIPTION, EFFECT_VALUE, characteristic);

        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION);
        equipment.getEffects().put(effect.getId(), effect);

        Character character = new Character(CHARAC_NAME);
        character.getCharacteristics().put(characteristic.getName(), characteristic);
        character.getEquipments().put(equipment.getName(), equipment);

        assertEquals(character.getCalculatedCaracteristics().get(CARAC_NAME), CARAC_VALUE + EFFECT_VALUE);
    }
}
