package com.multicar.control_merca.controller;

import com.multicar.control_merca.model.Mercaderia;
import com.multicar.control_merca.repository.MercaderiaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MercaderiaController {

    @Autowired
    private MercaderiaRepository repository;

    @GetMapping("/")
    public String index(@RequestParam(name = "q", required = false) String query, 
                        @RequestParam(name = "p", required = false) String proveedor,
                        @RequestParam(name = "verSolucionados", defaultValue = "false") boolean verSolucionados,
                        Model model) {
        
        List<Mercaderia> listaTotal = repository.findAll();

        if (proveedor != null && !proveedor.isEmpty()) {
            listaTotal = repository.findByProveedor(proveedor);
        } else if (query != null && !query.isEmpty()) {
            listaTotal = repository.findByCodigoInternoContainingIgnoreCase(query);
        }

        List<Mercaderia> listaFiltrada = listaTotal.stream()
            .filter(m -> verSolucionados ? "SOLUCIONADO".equals(m.getEstado()) : !"SOLUCIONADO".equals(m.getEstado()))
            .collect(Collectors.toList());

        model.addAttribute("lista", listaFiltrada);
        model.addAttribute("proveedores", repository.findDistinctProveedores());
        model.addAttribute("mercaderia", new Mercaderia()); 
        model.addAttribute("modoSolucionados", verSolucionados);
        model.addAttribute("pSeleccionado", proveedor);
        model.addAttribute("totalPendientes", repository.countByEstadoNot("SOLUCIONADO"));
        model.addAttribute("totalSolucionados", repository.countByEstado("SOLUCIONADO"));
        
        return "index";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Mercaderia mercaderia, RedirectAttributes ra) {
        if (mercaderia.getId() == null) {
            if (repository.findByCodigoInterno(mercaderia.getCodigoInterno()).isPresent()) {
                ra.addFlashAttribute("alerta", "¡Atención! El código " + mercaderia.getCodigoInterno() + " ya existe.");
            }
        }
        repository.save(mercaderia);
        return "redirect:/";
    }

    @GetMapping("/solucionar/{id}")
    public String solucionar(@PathVariable Long id) {
        repository.findById(id).ifPresent(m -> {
            m.setEstado("SOLUCIONADO");
            repository.save(m);
        });
        return "redirect:/";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Mercaderia m = repository.findById(id).orElse(new Mercaderia());
        model.addAttribute("mercaderia", m);
        model.addAttribute("lista", repository.findAll().stream().filter(x -> !"SOLUCIONADO".equals(x.getEstado())).collect(Collectors.toList()));
        model.addAttribute("proveedores", repository.findDistinctProveedores());
        model.addAttribute("totalPendientes", repository.countByEstadoNot("SOLUCIONADO"));
        model.addAttribute("totalSolucionados", repository.countByEstado("SOLUCIONADO"));
        return "index";
    }

    @GetMapping("/exportar")
    public void exportarAExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Mercaderia.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        Row headerRow = sheet.createRow(0);
        String[] columnas = {"Fecha", "Código", "Proveedor", "Descripción", "Cantidad", "Estado", "Observación"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        List<Mercaderia> mercaderias = repository.findAll();
        int rowNum = 1;
        for (Mercaderia m : mercaderias) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(m.getFechaManual());
            row.createCell(1).setCellValue(m.getCodigoInterno());
            row.createCell(2).setCellValue(m.getProveedor());
            row.createCell(3).setCellValue(m.getDescripcion());
            row.createCell(4).setCellValue(m.getCantidad());
            row.createCell(5).setCellValue(m.getEstado());
            row.createCell(6).setCellValue(m.getObservacion());
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}