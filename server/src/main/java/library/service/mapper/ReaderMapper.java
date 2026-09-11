package library.service.mapper;

import library.controller.dto.ReaderDto;
import library.domain.Reader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReaderMapper implements EntityMapper<Reader, ReaderDto> {

    private final ModelMapper modelMapper;

    public ReaderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Reader convertToEntity(ReaderDto readerDto) {
        Reader reader = modelMapper.map(readerDto, Reader.class);

        reader.setName(readerDto.getName());
        reader.setEmail(readerDto.getEmail());
        reader.setPhoneNumber(readerDto.getPhoneNumber());
        reader.setBirthdate(readerDto.getBirthdate());
        reader.setRegistered(readerDto.getRegistered());

        return reader;
    }

    @Override
    public ReaderDto convertToDto(Reader reader) {
        ReaderDto readerDto = modelMapper.map(reader, ReaderDto.class);

        readerDto.setName(reader.getName());
        readerDto.setEmail(reader.getEmail());
        readerDto.setPhoneNumber(reader.getPhoneNumber());
        readerDto.setBirthdate(reader.getBirthdate());
        readerDto.setRegistered(reader.getRegistered());

        return readerDto;
    }

    @Override
    public List<ReaderDto> convertManyToDto(List<Reader> readers) {
        return readers.stream().map(this::convertToDto).toList();
    }
}
