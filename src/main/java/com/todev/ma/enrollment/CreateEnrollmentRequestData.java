package com.todev.ma.enrollment;

import java.util.List;

record CreateEnrollmentRequestData(Long reference_id, List<EnrollmentChargeDetail> charges) {
}
