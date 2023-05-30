import DB.*;
import exceptions.MyException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;
@Path("")
public class Api {
    private Provider provider = new DBProviderImpl();

    @GET
    @Path("/renters/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getAllRenters() {
        System.out.println("/renters/all");
        return provider.getAllRenters();
    }

    @GET
    @Path("/renters/{renterId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterById(@PathParam("renterId") String renterId) {
        System.out.println("/renters/{renterId}");
        return provider.getRenterById(renterId);
    }

    @POST
    @Path("/renters/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Renter addRenter(Renter renter) {
        System.out.println("/renters/add");
        return provider.addRenter(renter);
    }

    @POST
    @Path("/renters/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Renter updateRenter(Renter renter) {
        System.out.println("/renters/update");
        return provider.updateRenter(renter);
    }

    @GET
    @Path("/renterInBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Renter getRenterByBox(@PathParam("boxId") String boxId) {
        System.out.println("/renterInBox/{boxId}");
        return provider.getRenterByBox(boxId);
    }

    @GET
    @Path("/renterByModel/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Renter> getRentersByModel(@PathParam("modelId") String modelId) {
        System.out.println("/renterByModel/{modelId}");
        return provider.getRentersByModel(modelId);
    }

    @GET
    @Path("/models/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Model> getAllModels() {
        System.out.println("/models/all");
        return provider.getAllModels();
    }

    @GET
    @Path("/models/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelById(@PathParam("modelId") String modelId) {
        System.out.println("/models/{modelId}");
        return provider.getModelById(modelId);
    }

    @POST
    @Path("/models/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Model addModel(Model model) {
        System.out.println("/models/add");
        return provider.addModel(model);
    }

    @POST
    @Path("/models/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Model updateModel(Model model) {
        System.out.println("/models/update");
        return provider.updateModel(model);
    }

    @GET
    @Path("/modelForBox/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Model getModelByBox(@PathParam("boxId") String boxId) {
        System.out.println("/modelForBox/{boxId}");
        return provider.getModelByBox(boxId);
    }

    @GET
    @Path("/boxes/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getAllBoxes() {
        System.out.println("/boxes/all");
        return provider.getAllBoxes();
    }

    @GET
    @Path("/boxes/free")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getFreeBoxes() {
        System.out.println("/boxes/free");
        return provider.getFreeBoxes();
    }

    @GET
    @Path("/boxes/freeWithModel/{modelId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Box> getFreeBoxesWithModel(@PathParam("modelId") String modelId) {
        System.out.println("/boxes/freeWithModel/{modelId}");
        return provider.getFreeBoxesWithModel(modelId);
    }

    @GET
    @Path("/boxes/{boxId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Box getBoxById(@PathParam("boxId") String boxId) {
        System.out.println("/boxes/{boxId}");
        return provider.getBoxById(boxId);
    }

    @POST
    @Path("/boxes/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Box addBox(Box box) {
        System.out.println("/boxes/add");
        return provider.addBox(box);
    }

    @POST
    @Path("/boxes/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Box updateBox(Box box) {
        System.out.println("/boxes/update");
        return provider.updateBox(box);
    }

    @DELETE
    @Path("/boxes/delete/{boxId}")
    public void deleteBoxById(@PathParam("boxId") String boxId) {
        System.out.println("/boxes/delete/{boxId}");
        provider.deleteBoxById(boxId);
    }

    @POST
    @Path("/boxes/costUp")
    @Consumes({MediaType.APPLICATION_JSON})
    public void changeCost(CostUp costUp) {
        System.out.println("/boxes/costUp");
        provider.changeCost(costUp);
    }

    @POST
    @Path("/cars/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Car addCar(Car car) {
        System.out.println("/cars/add");
        return provider.addCar(car);
    }

    @POST
    @Path("/cars/update")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Car updateCar(Car car) {
        System.out.println("/cars/update");
        return provider.updateCar(car);
    }

    @GET
    @Path("/cars/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Car> allCars() {
        System.out.println("/cars/all");
        return provider.getAllCars();
    }

    @DELETE
    @Path("/cars/delete/{carId}")
    public void deleteCarById(@PathParam("carId") String carId) {
        System.out.println("/cars/delete/{carId}");
        provider.deleteCarById(carId);
    }

    @GET
    @Path("/free_boxes.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFreeBoxesXlsx() {
        System.out.println("/free_boxes.xlsx");
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


    @GET
    @Path("/amount.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAmount(@QueryParam("car_number") String carNumber) {
        System.out.println("/amount.xlsx");
        Car car = provider.getCarById(carNumber);
        Renter renter = getRenterByBox(car.getBox_number().toString());
        Box box = getBoxById(car.getBox_number().toString());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Boxes");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setWrapText(true);
        XSSFFont headerFont = ((XSSFWorkbook) workbook).createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        int pos = 0;
        Row header = sheet.createRow(pos++);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Номер квитанции");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue(car.getReceipt_number());
        headerCell.setCellStyle(headerStyle);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue("ФИО арендатора");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue(car.getRenter_full_name());
        headerCell.setCellStyle(style);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue("Телефон");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue(car.getRenter_phone());
        headerCell.setCellStyle(style);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue("Адрес");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue(renter.getAddress());
        headerCell.setCellStyle(style);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue("Номер машины");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue(car.getAutomobile_number());
        headerCell.setCellStyle(style);

        sheet.createRow(pos++);
        sheet.createRow(pos++);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue("Номер бокса");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Посуточная стоимость \nаренды (руб/сутки)");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(2);
        headerCell.setCellValue("Дата начала аренды");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(3);
        headerCell.setCellValue("Количество (дней)");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(4);
        headerCell.setCellValue("Итого");
        headerCell.setCellStyle(style);

        header = sheet.createRow(pos++);
        headerCell = header.createCell(0);
        headerCell.setCellValue(box.getBox_number());
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue(box.getDaily_cost());
        headerCell.setCellStyle(style);
        headerCell = header.createCell(2);
        headerCell.setCellValue(car.getRental_start_date());
        headerCell.setCellStyle(style);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(car.getRental_start_date(), dtf);
        long daysNumber = Period.between(date, LocalDate.now()).getDays();
        headerCell = header.createCell(3);
        headerCell.setCellValue(daysNumber);
        headerCell.setCellStyle(style);
        headerCell = header.createCell(4);
        headerCell.setCellValue(daysNumber * box.getDaily_cost());
        headerCell.setCellStyle(style);
        return getXlsxFile(workbook);
    }

    private Response getRentersXlsx(List<Renter> renters, Car car) {
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
        headerCell.setCellValue("Полное имя");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Телефон");
        headerCell.setCellStyle(style);
        headerCell = header.createCell(2);
        headerCell.setCellValue("Адрес");
        headerCell.setCellStyle(style);
        if (car != null) {
            headerCell = header.createCell(3);
            headerCell.setCellValue("Номер квитанции");
            headerCell.setCellStyle(style);
            headerCell = header.createCell(4);
            headerCell.setCellValue("Номер машины");
            headerCell.setCellStyle(style);
        }
        for(Renter renter:renters) {
            Row line = sheet.createRow(pos++);
            Cell cell = line.createCell(0);
            cell.setCellValue(renter.getFull_name());
            cell.setCellStyle(style);
            cell = line.createCell(1);
            cell.setCellValue(renter.getPhone());
            cell.setCellStyle(style);
            cell = line.createCell(2);
            cell.setCellValue(renter.getAddress());
            cell.setCellStyle(style);
            if (car != null) {
                cell = line.createCell(3);
                cell.setCellValue(car.getReceipt_number());
                cell.setCellStyle(style);
                cell = line.createCell(4);
                cell.setCellValue(car.getAutomobile_number());
                cell.setCellStyle(style);
            }
        }
        return getXlsxFile(workbook);
    }

    @GET
    @Path("/client_in_box.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getClientXlsxByBoxId(@QueryParam("box_number") String boxId) throws MyException {
        System.out.println("/client_in_box.xlsx");
        if(boxId == null) {
            throw new MyException("Не задан box_number");
        }
        Renter renter = getRenterByBox(boxId);
        List<Renter> renters = new ArrayList<>();
        Car car = provider.getCarByBox(boxId);
        renters.add(renter);
        return getRentersXlsx(renters, car);
    }

    @GET
    @Path("/all_clients.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAllClientsXlsx() {
        System.out.println("/all_clients.xlsx");
        List<Renter> renters = getAllRenters();
        return getRentersXlsx(renters, null);
    }

    @GET
    @Path("/client_with_model.xlsx")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getClientXlsxByModelId(@QueryParam("id_model") String modelId) throws MyException {
        System.out.println("/client_with_model.xlsx");
        if(modelId == null) {
            throw new MyException("Не задан box_number");
        }
        List<Renter> renters = getRentersByModel(modelId);
        return getRentersXlsx(renters, null);
    }

    @GET
    @Path("/models/report")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ModelReport> getModelReport() {
        System.out.println("/models/report");
        Map<Integer, Integer> boxesWithModel = new HashMap<>();
        provider.getAllBoxes().forEach(box->boxesWithModel.put(box.getId_model(), boxesWithModel.getOrDefault(box.getId_model(), 0) + 1));
        Map<Integer, Integer> carsWithModel = new HashMap<>();
        provider.getAllCars().forEach(car->carsWithModel.put(car.getId_model(), carsWithModel.getOrDefault(car.getId_model(), 0) + 1));
        List<ModelReport> result = new ArrayList<>();
        provider.getAllModels().forEach(model->
                result.add(new ModelReport(model.getName(),
                        boxesWithModel.getOrDefault(model.getId_model(), 0),
                        carsWithModel.getOrDefault(model.getId_model(), 0))));
        return result;
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
