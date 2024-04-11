package org.example;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class ParcialTest {
    String rutaArchivoExcel = "/datos.xlsx";
    FileInputStream archivo = new FileInputStream(new File(rutaArchivoExcel));

    Workbook libroExcel = WorkbookFactory.create(archivo);

    Sheet hojaExcel = libroExcel.getSheetAt(0);

    public ParcialTest() throws IOException {
    }

    @Test
    void happy() {
        try{
        for (Row fila : hojaExcel) {
            // Itera sobre las celdas de la fila
            for (Cell celda : fila) {
                // Dependiendo del tipo de celda, lee el contenido
                switch (celda.getCellType()) {
                    case STRING:
                        System.out.print(celda.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(celda.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(celda.getBooleanCellValue() + "\t");
                        break;
                    default:
                        System.out.print("\t");
                }
            }
            System.out.println(); // Salto de línea después de cada fila
        }

        // Cierra el archivo
        archivo.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:4200/clientes/form");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.id("nit"));
        WebElement inputPnombre = driver.findElement(By.id("pnombre"));
        WebElement inputSnombre = driver.findElement(By.id("snombre"));
        WebElement inputPApellido = driver.findElement(By.id("pApellido"));
        WebElement inputSApellido = driver.findElement(By.id("sApellido"));
        WebElement inputFecha = driver.findElement(By.name("fecha_nacimiento"));
        WebElement inputTelefono = driver.findElement(By.id("telefono"));
        WebElement inputEmail = driver.findElement(By.id("email"));


        WebElement buttonEnviar = driver.findElement(By.id("crear"));


        textBox.sendKeys("Hola");
        buttonEnviar.click();
        driver.quit();
    }

    @Test
    void noHappy() {
    }


}
