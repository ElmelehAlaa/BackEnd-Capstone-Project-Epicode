package HighScore.CapStoneProject.controllers;

import HighScore.CapStoneProject.entities.ListService;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.payload.ListServiceDTO;
import HighScore.CapStoneProject.services.ListServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listservice")
public class ListServiceController {
    @Autowired
    private ListServiceService listServiceService;

    @GetMapping("")
    public Page<ListService> getListService(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String orderBy) {
        return listServiceService.getListService(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ListService saveListService(@RequestBody @Validated ListServiceDTO body, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {

            return listServiceService.save(body);
        }
    }

    @GetMapping(value = "/{id}")
    public ListService findById(@PathVariable long id) {
        return listServiceService.findListServiceById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        listServiceService.findByIdAndDelete(id);
    }

}
