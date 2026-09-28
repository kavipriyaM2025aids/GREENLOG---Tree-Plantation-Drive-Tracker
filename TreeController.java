package com.greenlog.controller;

import com.greenlog.dto.TreeRequest;
import com.greenlog.entity.Tree;
import com.greenlog.enums.TreeStatus;
import com.greenlog.service.TreeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tree createTree(@Valid @RequestBody TreeRequest request) {
        return treeService.createTree(request);
    }

    @GetMapping
    public List<Tree> getAllTrees() {
        return treeService.getAllTrees();
    }

    @GetMapping("/{id}")
    public Tree getTreeById(@PathVariable Long id) {
        return treeService.getTreeById(id);
    }

    @PutMapping("/{id}")
    public Tree updateTree(@PathVariable Long id, @Valid @RequestBody TreeRequest request) {
        return treeService.updateTree(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
    }

    @GetMapping("/drive/{driveId}")
    public List<Tree> getTreesByDrive(@PathVariable Long driveId) {
        return treeService.getTreesByDrive(driveId);
    }

    @GetMapping("/species/{species}")
    public List<Tree> getTreesBySpecies(@PathVariable String species) {
        return treeService.getTreesBySpecies(species);
    }

    @GetMapping("/status/{status}")
    public List<Tree> getTreesByStatus(@PathVariable TreeStatus status) {
        return treeService.getTreesByStatus(status);
    }

    @GetMapping("/due-checkins")
    public List<Tree> getDueCheckIns() {
        return treeService.getDueCheckIns();
    }
}
