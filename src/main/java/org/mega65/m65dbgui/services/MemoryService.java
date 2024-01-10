package org.mega65.m65dbgui.services;

import jakarta.annotation.Priority;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Adressspace;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.domain.Mapping;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.operations.M65Go;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.operations.M65Registers;
import org.mega65.m65dbgui.operations.M65Store;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class MemoryService {

    @Inject State state;
    @Inject AdressspaceService adressspaceService;
    String mapl = "";
    String maph = "";
    List<Mapping> mapping;
    Logger logger = Util.getLogger(MemoryService.class);

    public void poke(long adr, long value) {
        M65Store store = new M65Store();
        store.setAdr(adr);
        store.setValue(value);
        state.executeDirect(store);
    }

    public void poke(List<Byte> bytes) {
        M65Store store = new M65Store();
        store.setBytes(bytes);
        state.executeDirect(store);
    }

    public void poke(Byte by) {
        poke(by.getAddr(), by.getValue());
    }

    public void asyncPoke(Byte by) {
        M65Store store = new M65Store();
        store.setByte(by);
        DiUtil.fireEvent(store);
    }

    public long peek(long addr) {
        M65Memory memory = new M65Memory();
        memory.setAdr(addr);
        memory = (M65Memory) state.executeDirect(memory);
        Byte by = memory.getBytes().get(0);
        return by.getValue();
    }

    public void go(long addr) {
        M65Go go = new M65Go();
        go.setAddr(addr);
        state.executeDirect(go);
    }

    public List<Mapping> getMapping() {
        return mapping;
    }

    private void initMapping(String mappingType, String data) {

        long start; // 0=MAPL  32768=MAPH

        switch (mappingType) {
            case Mapping.MAPPING_TYPE_D030:
                logger.info("$D030 Mapping");
                break;
            case Mapping.MAPPING_TYPE_01:
                logger.info("$01 Mapping");
                break;
            case Mapping.MAPPING_TYPE_MAPL:
            case Mapping.MAPPING_TYPE_MAPH:
                if (data.equals(""))
                    break;

                String first = Util.toBinary2(Util.fromHex(data.substring(0,1)));
                String last = data.substring(1,4) + "00";

                if (mappingType.equals(Mapping.MAPPING_TYPE_MAPH)) {
                    start = 32768;
                } else {
                    start = 0;
                }
                List<String> bits = Arrays.stream(first.split("")).toList();
                List reverseBits = IntStream.range(0, bits.size()).map(i -> bits.size() - 1- i).mapToObj(bits::get).collect(Collectors.toList());
                reverseBits.stream().forEach(x -> logger.info("-> " + x));
                reverseBits.forEach( Util.withCounter((index , bit) -> {
                    logger.info("i,mappingSegment -> " + index + ","  + bit);
                    if (bit.equals("1")) {
                        long adrStart = start + index * 8192;
                        long adrEnd = adrStart + 8192;
                        long offset = Util.fromHex(last);
                        long source = adrStart + offset;
                        long pos = start+index;
                        Adressspace adr = adressspaceService.getAdressspaceByAdr(source);
                        mapping.add(new Mapping(mappingType, adr.available(), adr.description(), adrStart, adrEnd, offset, source, pos));
                    }
                }));
        }



    }

    public void onGenericEvent(@Observes GenericEvent event) {
        if (event.getEvent().equals(GenericEvent.GENERIC_EVENT_MAPPING_REFRESH)) {
            M65Registers registers = (M65Registers) event.getData();
            if (!mapl.equals(registers.getMapl()) || !maph.equals(registers.getMaph())) {
                logger.info("onGenericEvent Prio 1");
                this.mapl = registers.getMapl();
                this.maph = registers.getMaph();

                mapping = new ArrayList<>();
                initMapping(Mapping.MAPPING_TYPE_MAPL, registers.getMapl());
                initMapping(Mapping.MAPPING_TYPE_MAPH, registers.getMaph());

                mapping.forEach( m -> logger.info(m.toString()));
                DiUtil.fireEvent(new GenericEvent((GenericEvent.GENERIC_EVENT_MAPPING_HAS_BEEN_REFRESHED)));

            }
        }
    }

}
