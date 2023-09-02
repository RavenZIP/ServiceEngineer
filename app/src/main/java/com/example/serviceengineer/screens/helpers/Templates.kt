package com.example.serviceengineer.screens.helpers

import android.os.Environment
import androidx.compose.runtime.Composable
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/** Шаблон заявок (акт приема-передачи) **/
fun requestTemplate(){
    val indexRequest = requestList.indices.find { requestList[it].id == requestId.value }
    val indexClient = /*clientList.indices.find { clientList[it].clientPhone == requestList[indexRequest!!].clientPhone }*/0
    val docNumber = indexRequest?.plus(1)
    val targetDoc = createWordDoc()
    addTitle(targetDoc, "Акт приема-передачи №$docNumber от ${requestList[indexRequest!!].requestDate}")
    addText(
        targetDoc,
        "Товар принят для проведения платного/гарантийного ремонта.",
        italic = false,
        bold = false
    )
    addText(
        targetDoc,
        "Примерное время диагностики и ремонта составит_________________",
        italic = false,
        bold = false
    )
    addText(
        targetDoc,
        "Заказчик: ${clientList[indexClient!!].clientSurname} ${clientList[indexClient].clientName} " +
                clientList[indexClient].clientPatronymic,
        italic = false,
        bold = false
    )
    addText(
        targetDoc,
        "Номер телефона: ${clientList[indexClient].clientPhone}",
        italic = false,
        bold = false
    )
    addText(
        targetDoc,
        "Я согласен (согласна) с тем, что в процессе диагностики и ремонта товара возможна потеря моих личных данных с любых носителей информации.",
        italic = true,
        bold = false
    )
    addText(
        targetDoc,
        "Подпись_________________",
        italic = true,
        bold = false
    )
    addText(
        targetDoc,
        "Инженер: ${uSurname.value} ${uName.value} ${uPatronymic.value}",
        italic = false,
        bold = false
    )
    addDeviceTable(targetDoc, indexRequest)
    targetDoc.createParagraph()
    addText(
        targetDoc,
        "С правилами оказания услуг ознакомлен, предоставленные мною данные верны",
        italic = true,
        bold = true
    )
    addText(
        targetDoc,
        "Подпись заказчика__________________",
        italic = true,
        bold = true
    )
    addText(
        targetDoc,
        "Подпись исполнителя_________________",
        italic = true,
        bold = true
    )
    saveOurDoc(targetDoc, "Заявка")
}


fun createWordDoc(): XWPFDocument {
    return XWPFDocument()
}

fun addTitle(targetDoc:XWPFDocument, text: String){
    val paragraph1 = targetDoc.createParagraph()
    paragraph1.alignment = ParagraphAlignment.CENTER

    val sentenceRun1 = paragraph1.createRun()

    sentenceRun1.isBold = true
    sentenceRun1.fontSize = 14
    sentenceRun1.fontFamily = "Calibri (Основной текст)"
    sentenceRun1.setText(text)
    sentenceRun1.addBreak()
}

fun addText(
    targetDoc:XWPFDocument,
    text: String,
    italic: Boolean,
    bold: Boolean
){
    val paragraph1 = targetDoc.createParagraph()
    paragraph1.alignment = ParagraphAlignment.LEFT

    val sentenceRun2 = paragraph1.createRun()
    sentenceRun2.fontSize = 12
    sentenceRun2.fontFamily = "Calibri (Основной текст)"
    sentenceRun2.isItalic = italic
    sentenceRun2.isBold = bold
    sentenceRun2.setText(text)
    sentenceRun2.addBreak()
}

fun addDeviceTable(targetDoc:XWPFDocument, index: Int){
    val ourTable = targetDoc.createTable()

    val row2 = ourTable.getRow(0)
    row2.getCell(0).text = "Устройство"
    row2.addNewTableCell().text = "${requestList[index].deviceType} ${requestList[index].deviceManufacturer} ${requestList[index].deviceModel}"

    val row3 = ourTable.createRow()
    row3.getCell(0).text = "Внешний вид"
    row3.getCell(1).text = requestList[index].deviceAppearance

    val row4 = ourTable.createRow()
    row4.getCell(0).text = "Комплектация"
    row4.getCell(1).text = requestList[index].deviceKit

    val row5 = ourTable.createRow()
    row5.getCell(0).text = "Неисправность"
    row5.getCell(1).text = requestList[index].deviceFault
    ourTable.setCellMargins(150, 100, 0, 1000)
}

fun saveOurDoc(targetDoc: XWPFDocument, filename: String){
    val storagePath = File(Environment.getExternalStorageDirectory(), "ServiceEngineer")
    if (!storagePath.exists()) {
        storagePath.mkdirs()
    }

    val wordFile = File(storagePath, "$filename.docx")
    try {
        val fileOut = FileOutputStream(wordFile)
        targetDoc.write(fileOut)
        fileOut.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}