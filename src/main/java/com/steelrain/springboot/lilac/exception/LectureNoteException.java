package com.steelrain.springboot.lilac.exception;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import lombok.Getter;

public class LectureNoteException extends LilacException{
    @Getter
    private Long lectureNoteId;
    @Getter
    private Long memberId;

    @Getter
    private LectureNoteDTO lectureNoteDTO;

    public LectureNoteException(String msg){
        super(msg);
    }
    public LectureNoteException(String msg, Exception e){
        super(msg, e);
    }

    public LectureNoteException(String msg, Long memberId){
        super(msg);
        this.memberId = memberId;
    }

    public LectureNoteException(String msg, Exception e, Long memberId){
        super(msg, e);
        this.memberId = memberId;
    }

    public LectureNoteException(String msg, Exception e, LectureNoteDTO lectureNoteDTO){
        super(msg, e);
        this.lectureNoteDTO = lectureNoteDTO;
    }

    public LectureNoteException(String msg, Exception e, Long lectureNoteId, Long memberId){
        super(msg, e);
        this.lectureNoteId = lectureNoteId;
        this.memberId = memberId;
    }
}
