package kr.mashup.branding.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 868951284L;

    public static final QMember member = new QMember("member1");

    public final kr.mashup.branding.domain.QBaseEntity _super = new kr.mashup.branding.domain.QBaseEntity(this);

    public final ListPath<kr.mashup.branding.domain.attendance.Attendance, kr.mashup.branding.domain.attendance.QAttendance> attendances = this.<kr.mashup.branding.domain.attendance.Attendance, kr.mashup.branding.domain.attendance.QAttendance>createList("attendances", kr.mashup.branding.domain.attendance.Attendance.class, kr.mashup.branding.domain.attendance.QAttendance.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath danggnPushNotificationAgreed = createBoolean("danggnPushNotificationAgreed");

    public final StringPath fcmToken = createString("fcmToken");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath identification = createString("identification");

    public final ListPath<MemberGeneration, QMemberGeneration> memberGenerations = this.<MemberGeneration, QMemberGeneration>createList("memberGenerations", MemberGeneration.class, QMemberGeneration.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final BooleanPath newsPushNotificationAgreed = createBoolean("newsPushNotificationAgreed");

    public final EnumPath<OsType> osType = createEnum("osType", OsType.class);

    public final StringPath password = createString("password");

    public final BooleanPath privatePolicyAgreed = createBoolean("privatePolicyAgreed");

    public final EnumPath<MemberStatus> status = createEnum("status", MemberStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

