package org.example.zippyziggy.Service;

import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.Domain.Magazine;
import org.example.zippyziggy.Repository.MagazineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MagazineService {

    private final MagazineRepository magazineRepository;

    public List<Magazine> getAllMagazines() {
        return magazineRepository.findAll();
    }

    public Magazine createMagazine(String title, String content) {
        Magazine magazine = new Magazine(title, content);
        return magazineRepository.save(magazine);
    }
}
