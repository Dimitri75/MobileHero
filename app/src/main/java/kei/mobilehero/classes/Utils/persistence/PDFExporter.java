package kei.mobilehero.classes.utils.persistence;

import android.content.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import kei.mobilehero.R;
import kei.mobilehero.classes.attributes.Characteristic;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Vuzi on 19/07/2015.
 */
public class PDFExporter {


    private static String generateExportString(Context context, Character c) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<center><h1>MobileHero</h1></center>");
        sb.append("<br/><br/>");
        sb.append(generateExportAttributes(context, c));
        sb.append("<br/><br/>");
        sb.append(generateExportCharacteristics(context, c));
        sb.append("<br/><br/>");
        sb.append(generateExportSkills(context, c));
        sb.append("<br/><br/>");
        sb.append(generateExportEquipment(context, c));
        sb.append("<br/>");
        sb.append("<html/>");

        return sb.toString();
    }

    private static String generateExportAttributes(Context context, Character c) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>" + context.getString(R.string.attributes) + "</h2>");
        sb.append("<br/>");

        sb.append("<ul>");
        sb.append("<li><b>" + context.getString(R.string.nameLabel) + "</b> : " + c.getName()   + "</li>");
        sb.append("<li><b>" + context.getString(R.string.level) + "</b> : " + c.getLevel()  + "</li>");
        sb.append("<li><b>" + context.getString(R.string.lifeLabel) + "</b> : " + c.getLife()   + "</li>");
        sb.append("<li><b>" + context.getString(R.string.manaLabel) + "</b> : " + c.getMana()   + "</li>");
        sb.append("<li><b>" + context.getString(R.string.sexLabel) +  "</b> : " + c.getGender() + "</li>");
        sb.append("<li><b>" + context.getString(R.string.raceLabel) + "</b> : " + c.getRace()   + "</li>");
        sb.append("<li><b>" + context.getString(R.string.classNameLabel) + "</b> : " + c.getClassName() + "</li>");
        sb.append("<li><b>" + context.getString(R.string.alignmentLabel) + "</b> : " + c.getAlignment() + "</li>");
        sb.append("<li><b>" + context.getString(R.string.weightLabel)    + "</b> : " + c.getEquipmentWeight() + "</li>");
        sb.append("</ul>");

        return sb.toString();
    }

    private static String generateExportCharacteristics(Context context, Character c) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>" + context.getString(R.string.characteristics) + "</h2>");
        sb.append("<br/>");

        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<th>" + context.getString(R.string.nameLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.descriptionLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.valueLabel) + "</th>");
        sb.append("</tr>");

        for(Characteristic charac : c.getCharacteristics().values()) {
            sb.append("<tr>");
            sb.append("<td>" + charac.getName() + "</td>");
            sb.append("<td>" + charac.getDescription() + "</td>");
            sb.append("<td>" + charac.getValue() + "</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    private static String generateExportSkills(Context context, Character c) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>" + context.getString(R.string.skill) + "</h2>");
        sb.append("<br/>");

        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<th>" + context.getString(R.string.nameLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.descriptionLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.effectLabel) + "</th>");
        sb.append("</tr>");

        for(Skill skill : c.getSkills().values()) {
            sb.append("<tr>");
            sb.append("<td>" + skill.getName() + "</td>");
            sb.append("<td>" + skill.getDescription() + "</td>");
            sb.append("<td><ul>");
            for(Effect e : skill.getEffects().values()) {
                sb.append("<li>" + e.toString() + "</li>");
            }
            sb.append("</ul></td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    private static String generateExportEquipment(Context context, Character c) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>" + context.getString(R.string.equipment) + "</h2>");
        sb.append("<br/>");

        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<th>" + context.getString(R.string.nameLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.descriptionLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.positionLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.weightLabel) + "</th>");
        sb.append("<th>" + context.getString(R.string.effectLabel) + "</th>");
        sb.append("</tr>");

        for(Equipment equipment : c.getEquipments().values()) {
            sb.append("<tr>");
            sb.append("<td>" + equipment.getName() + "</td>");
            sb.append("<td>" + equipment.getDescription() + "</td>");
            sb.append("<td>" + equipment.getEquipmentPosition() + "</td>");
            sb.append("<td>" + equipment.getWeight() + "</td>");
            sb.append("<td><ul>");
            for(Effect e : equipment.getEffects().values()) {
                sb.append("<li>" + e.toString() + "</li>");
            }
            sb.append("</ul></td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    public static void export(Context context, Character c, File f) throws IOException, DocumentException {
        Document document = new Document();
        OutputStream fileOut = new FileOutputStream(f);

        PdfWriter.getInstance(document, fileOut);
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(generateExportString(context, c)));

        document.close();
        fileOut.close();
    }

}
