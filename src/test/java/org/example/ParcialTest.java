package org.example;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ParcialTest {
    String rutaArchivoExcel = "datos1.xlsx";
    FileInputStream archivo = new FileInputStream(new File(rutaArchivoExcel));
    Workbook libroExcel = WorkbookFactory.create(archivo);
    Sheet hojaExcel = libroExcel.getSheetAt(0);
    SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat formatoDeseado = new SimpleDateFormat("d/MM/yyyy");
    public ParcialTest() throws IOException {
    }
    @Test
    void chrome() {
        WebDriver driver = new ChromeDriver();
        ejecucionPrueba(driver);
    }
    public void limpiezaDeCampos(WebDriver driver,WebElement buttonEnviar, WebElement inputNit,WebElement inputPnombre, WebElement inputSnombre,
                                 WebElement inputPApellido,WebElement inputSApellido,WebElement inputFecha,WebElement inputTelefono,
                                 WebElement inputEmail){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("El botón no está habilitado y no se puede hacer clic en él.");
        inputNit.clear();
        inputPnombre.clear();
        inputSnombre.clear();
        inputPApellido.clear();
        inputSApellido.clear();
        js.executeScript("arguments[0].value = '';", inputFecha);
        inputTelefono.clear();
        inputEmail.clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }
    @Test
    void edge() {
        WebDriver driver = new EdgeDriver();
        ejecucionPrueba(driver);
    }

    @Test
    void firefox(){
        WebDriver driver = new FirefoxDriver();
        ejecucionPrueba(driver);
    }
    public void ejecucionPrueba(WebDriver driver){
        driver.get("http://localhost:4200/clientes/form");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement inputNit = driver.findElement(By.id("nit"));
        WebElement inputPnombre = driver.findElement(By.id("pnombre"));
        WebElement inputSnombre = driver.findElement(By.id("snombre"));
        WebElement inputPApellido = driver.findElement(By.id("pApellido"));
        WebElement inputSApellido = driver.findElement(By.id("sApellido"));
        WebElement inputFecha = driver.findElement(By.name("fecha_nacimiento"));
        WebElement inputTelefono = driver.findElement(By.id("telefono"));
        WebElement inputEmail = driver.findElement(By.id("email"));

        WebElement buttonEnviar = driver.findElement(By.id("crear"));

        for (Row fila : hojaExcel) {
            int i = 0;
            for (Cell celda : fila) {
                if (celda.toString().equals("null")) break;
                switch (i) {
                    case 0 -> {
                        inputNit.sendKeys(celda.toString());
                    }
                    case 1 -> {

                        inputPnombre.sendKeys(celda.toString());
                    }
                    case 2 -> {

                        inputSnombre.sendKeys(celda.toString());
                    }
                    case 3 -> {

                        inputPApellido.sendKeys(celda.toString());
                    }
                    case 4 -> {

                        inputSApellido.sendKeys(celda.toString());
                    }
                    case 5 -> {
                        try {
                            Date fecha = formatoOriginal.parse(celda.toString());

                            String nuevaFecha = formatoDeseado.format(fecha);
                            inputFecha.sendKeys(nuevaFecha);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    case 6 -> {

                        inputTelefono.sendKeys(celda.toString());
                    }
                    case 7 -> {
                        inputEmail.sendKeys(celda.toString());
                    }
                }
                i++;

            }
            if (buttonEnviar.isEnabled()) {
                buttonEnviar.click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000)); // Espera hasta 10 segundos
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal2-popup")));

                WebElement swal2Popup = driver.findElement(By.className("swal2-popup"));
                assert swal2Popup.isDisplayed();

                WebElement elemento = driver.findElement(By.xpath("/html/body/div/div/div[1]/span"));

                String clases = elemento.getAttribute("class");
                boolean tieneClase = clases.contains("swal2-x-mark");
                if (tieneClase){
                    Assertions.assertEquals("swal2-x-mark",clases);
                    WebElement boton = driver.findElement(By.xpath("/html/body/div/div/div[6]/button[1]"));
                    boton.click();
                    limpiezaDeCampos(driver, buttonEnviar,inputNit,inputPnombre, inputSnombre, inputPApellido, inputSApellido, inputFecha, inputTelefono, inputEmail);
                    break;
                }else{
                    Assertions.assertEquals("swal2-success-line-tip",clases);
                    break;
                }
            } else {
                limpiezaDeCampos(driver,buttonEnviar, inputNit,inputPnombre, inputSnombre, inputPApellido, inputSApellido, inputFecha, inputTelefono, inputEmail);
            }

        }
        try
        {
            archivo.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
