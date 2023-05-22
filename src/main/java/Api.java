import DB.*;
import exceptions.MyException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
@Path("")
public class Api {
    private Provider provider = new DBProviderImpl();

    @GET
    @Path("/renters/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getAllRenters() {
        return provider.getAllRenters();
    }

    @GET
    @Path("/renters/{renterId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterById(@PathParam("renterId") String renterId) {
        return provider.getRenterById(renterId);
    }

    @POST
    @Path("/renters/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Renter addRenter(Renter renter) {
        return provider.addRenter(renter);
    }

    @GET
    @Path("/renterInBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterByBox(@PathParam("boxId") String boxId) {
        return provider.getRenterByBox(boxId);
    }

    @GET
    @Path("/renterByModel/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getRentersByModel(@PathParam("modelId") String modelId) {
        return provider.getRentersByModel(modelId);
    }

    @GET
    @Path("/models/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Model> getAllModels() {
        return provider.getAllModels();
    }

    @GET
    @Path("/models/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelById(@PathParam("modelId") String modelId) {
        return provider.getModelById(modelId);
    }

    @POST
    @Path("/models/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Model addModel(Model model) {
        return provider.addModel(model);
    }

    @GET
    @Path("/modelForBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelByBox(@PathParam("boxId") String boxId) {
        return provider.getModelByBox(boxId);
    }

    @GET
    @Path("/boxes/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getAllBoxes() {
        return provider.getAllBoxes();
    }

    @GET
    @Path("/boxes/free")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getFreeBoxes() {
        return provider.getFreeBoxes();
    }

    @GET
    @Path("/boxes/freeWithModel/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getFreeBoxesWithModel(@PathParam("modelId") String modelId) {
        return provider.getFreeBoxesWithModel(modelId);
    }

    @GET
    @Path("/boxes/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Box getBoxById(@PathParam("boxId") String boxId) {
        return provider.getBoxById(boxId);
    }

    @POST
    @Path("/boxes/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Box addBox(Box box) {
        return provider.addBox(box);
    }

    @DELETE
    @Path("/boxes/delete/{boxId}")
    public void deleteBoxById(@PathParam("boxId") String boxId) {
        provider.deleteBoxById(boxId);
    }

    @POST
    @Path("/boxes/costUp")
    @Consumes({MediaType.APPLICATION_JSON})
    public void changeCost(CostUp costUp) {
        provider.changeCost(costUp);
    }

    @POST
    @Path("/cars/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Car addCar(Car car) {
        return provider.addCar(car);
    }

    @GET
    @Path("/cars/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Car> allCars() {
        return provider.getAllCars();
    }

    @DELETE
    @Path("/cars/delete/{carId}")
    public void deleteCarById(@PathParam("carId") String carId) {
        provider.deleteCarById(carId);
    }

    @GET
    @Path("/free_boxes.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFreeBoxesXlsx() {
        List<Box> freeBoxes = getFreeBoxes();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Boxes");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        int pos = 0;
        Row header = sheet.createRow(pos++);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Номер бокса");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Стоимость в день");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(2);
        headerCell.setCellValue("Имя модели");
        headerCell.setCellStyle(style);
        for(var box : freeBoxes) {
            Row line = sheet.createRow(pos++);
            Cell cell = line.createCell(0);
            cell.setCellValue(box.getBox_number());
            cell.setCellStyle(style);
            cell = line.createCell(1);
            cell.setCellValue(box.getDaily_cost());
            cell.setCellStyle(style);
            cell = line.createCell(2);
            cell.setCellValue(box.getModel_name());
            cell.setCellStyle(style);
        }
        return getXlsxFile(workbook);
    }

    private Response getRentersXlsx(List<Renter> renters) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Renter");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        int pos = 0;
        Row header = sheet.createRow(pos++);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Номер арендатора");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Полное имя");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(2);
        headerCell.setCellValue("Телефон");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(3);
        headerCell.setCellValue("Адрес");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(4);
        headerCell.setCellValue("Номер квитанции");
        headerCell.setCellStyle(style);
        for(Renter renter:renters) {
            Row line = sheet.createRow(pos++);
            Cell cell = line.createCell(0);
            cell.setCellValue(renter.getId_renter());
            cell.setCellStyle(style);
            cell = line.createCell(1);
            cell.setCellValue(renter.getFull_name());
            cell.setCellStyle(style);
            cell = line.createCell(2);
            cell.setCellValue(renter.getPhone());
            cell.setCellStyle(style);
            cell = line.createCell(3);
            cell.setCellValue(renter.getAddress());
            cell.setCellStyle(style);
            cell = line.createCell(4);
            cell.setCellValue(renter.getReceipt_number());
            cell.setCellStyle(style);
        }
        return getXlsxFile(workbook);
    }

    @GET
    @Path("/client_in_box.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getClientXlsxByBoxId(@QueryParam("box_number") String boxId) throws MyException {
        if(boxId == null) {
            throw new MyException("Не задан box_number");
        }
        Renter renter = getRenterByBox(boxId);
        List<Renter> renters = new ArrayList<>();
        renters.add(renter);
        return getRentersXlsx(renters);
    }

    @GET
    @Path("/all_clients.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAllClientsXlsx() {
        List<Renter> renters = getAllRenters();
        return getRentersXlsx(renters);
    }

    @GET
    @Path("/client_with_model.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getClientXlsxByModelId(@QueryParam("id_model") String modelId) throws MyException {
        if(modelId == null) {
            throw new MyException("Не задан box_number");
        }
        List<Renter> renters = getRentersByModel(modelId);
        return getRentersXlsx(renters);
    }


    private Response getXlsxFile(Workbook workbook) {
        try {
            String fileLocation = Files.createTempFile("", ".tmp").toString();
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            FileInputStream inputStream = new FileInputStream(fileLocation);
            return Response.status(200).entity(inputStream).build();
        } catch (IOException e) {
            throw new MyException("Непредвиденная ошибка с файлами:\n" + e);
        }
    }

}
