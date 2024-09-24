using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using UniversityApi.DTO;
using UniversityApi.Models;
using UniversityApi.Repositories;
using UniversityApi.Repositories.Interfaces;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddScoped<IDbContext, DbContext>(provider => 
new DbContext(builder.Configuration.GetConnectionString("UniDbConnectionString")));
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var app = builder.Build();

// Configure the HTTP request pipeline.

app.UseHttpsRedirection();

#region Student Endpoints
app.MapGet("/api/students", async (IDbContext studentRepository) =>
{
    return await studentRepository.GetAllStudents();
});

app.MapGet("/api/student/{id}", async (IDbContext repo, int id) =>
{
    var studentDetails = new StudentDetailsDto();
    var student = await repo.GetStudentById(id);
    if (student == null)
    {
        return Results.NotFound();
    }
    var courses = await repo.GetCoursesByStudentId(id);
    studentDetails.Courses = courses.ToList();
    studentDetails.StudentId = student.StudentId;
    studentDetails.FirstName = student.FirstName;
    studentDetails.LastName = student.LastName;
    studentDetails.GradeLevel = student.GradeLevel;

    return Results.Ok(studentDetails);
});

app.MapPost("/api/student", async (IDbContext repo, Student student) =>
{
    if (string.IsNullOrEmpty(student.FirstName) ||
        string.IsNullOrEmpty(student.LastName) ||
        student.GradeLevel < 1 ||
        student.GradeLevel > 4)
    {
        return Results.BadRequest();
    }
    int studentId = await repo.AddStudent(student);
    student.StudentId = studentId;
    return Results.Ok(student);
});

app.MapPut("/api/student", async (IDbContext repo, Student student) =>
{
    if (string.IsNullOrEmpty(student.FirstName) ||
           string.IsNullOrEmpty(student.LastName) ||
                  student.GradeLevel < 1 ||
                         student.GradeLevel > 4)
    {
        return Results.BadRequest();
    }
    var studentToUpdate = await repo.GetStudentById(student.StudentId);
    if (studentToUpdate == null)
    {
        return Results.NotFound();
    }
    studentToUpdate.FirstName = student.FirstName;
    studentToUpdate.LastName = student.LastName;
    studentToUpdate.GradeLevel = student.GradeLevel;
    await repo.UpdateStudent(studentToUpdate);
    return Results.Ok(studentToUpdate);
});

app.MapDelete("/api/student/{id}", async (IDbContext repo, int id) =>
{
    var studentToDelete = await repo.GetStudentById(id);
    if (studentToDelete == null)
    {
        return Results.NotFound();
    }
    await repo.DeleteStudent(id);
    return Results.Ok();
});
#endregion

#region Teacher Endpoints
app.MapGet("/api/teachers", async (IDbContext teacherRepository) =>
{
    return await teacherRepository.GetAllTeachers();
});

app.MapGet("/api/teacher/{id}", async (IDbContext repo, int id) =>
{
    var teacher = await repo.GetTeacherById(id);
    if (teacher == null)
    {
        return Results.NotFound();
    }
    TeacherDetailsDto teacherDetails = new TeacherDetailsDto(teacher);
    var courses = await repo.GetCoursesByTeacherId(id);
    teacherDetails.Courses = courses.ToList();
    return Results.Ok(teacherDetails);
});

app.MapPost("/api/teacher", async (IDbContext repo, Teacher teacher) =>
{
    if (string.IsNullOrEmpty(teacher.FirstName) ||
           string.IsNullOrEmpty(teacher.LastName))
    {
        return Results.BadRequest();
    }
    int teacherId = await repo.AddTeacher(teacher);
    teacher.TeacherId = teacherId;
    return Results.Ok(teacher);
});

app.MapPut("/api/teacher", async (IDbContext repo, Teacher teacher) =>
{
    if (string.IsNullOrEmpty(teacher.FirstName) ||
           string.IsNullOrEmpty(teacher.LastName))
    {
        return Results.BadRequest();
    }
    var teacherToUpdate = await repo.GetTeacherById(teacher.TeacherId);
    if (teacherToUpdate == null)
    {
        return Results.NotFound();
    }
    teacherToUpdate.FirstName = teacher.FirstName;
    teacherToUpdate.LastName = teacher.LastName;
    await repo.UpdateTeacher(teacherToUpdate);
    return Results.Ok(teacherToUpdate);
});
app.MapDelete("/api/teacher/{id}", async (IDbContext repo, int id) =>
{
    var teacherToDelete = await repo.GetTeacherById(id);
    if (teacherToDelete == null)
    {
        return Results.NotFound();
    }
    await repo.DeleteTeacher(id);
    return Results.Ok();
});
#endregion

#region Course Endpoints
app.MapGet("/api/courses", async (IDbContext courseRepository) =>
{
    return await courseRepository.GetAllCourses();
});

app.MapPost("/api/course", async (IDbContext repo, Course course) =>
{
    if (string.IsNullOrEmpty(course.CourseName) ||
           course.Credits < 1)
    {
        return Results.BadRequest();
    }
    int courseId = await repo.AddCourse(course);
    course.CourseId = courseId;
    return Results.Ok(course);
});

app.MapDelete("/api/course/{id}", async (IDbContext repo, int id) =>
{
    Course courseToDelete = await repo.GetCourseById(id);
    if (courseToDelete == null)
    {
        return Results.NotFound();
    }
    await repo.DeleteCourse(id);
    return Results.Ok();
});
#endregion

#region Enrollment Endpoints
app.MapPost("/api/enrollment", async (IDbContext repo, Enrollment enrollment) =>
{
    Enrollment existingEnrollment = await repo.GetEnrollmentByStudentAndCourseId(enrollment.StudentId, enrollment.CourseId);
    if (existingEnrollment != null)
    {
        return Results.BadRequest();
    }
    try
    {
        int enrollmentId = await repo.AddEnrollment(enrollment);
        enrollment.EnrollmentId = enrollmentId;
    }
    catch (Exception)
    {
        return Results.BadRequest();
    }
    return Results.Ok(enrollment);
});

app.MapDelete("/api/enrollment/{studentId}/{courseId}", async (IDbContext repo, int studentId, int courseId) =>
{
    Enrollment enrollmentToDelete = await repo.GetEnrollmentByStudentAndCourseId(studentId, courseId);
    if (enrollmentToDelete == null)
    {
        return Results.NotFound();
    }
    await repo.DeleteEnrollment(enrollmentToDelete.EnrollmentId);
    return Results.Ok();
});
#endregion

#region Grade Endpoints
app.MapPost("/api/grade", async (IDbContext repo, Grade grade) =>
{
    if (grade.GradeValue < 2 || grade.GradeValue > 6)
    {
        return Results.BadRequest();
    }
    try
    {
        int gradeId = await repo.AddGrade(grade);
        grade.GradeId = gradeId;
    }
    catch (Exception)
    {
        return Results.BadRequest();
    }
    
    return Results.Ok(grade);
});

app.MapDelete("/api/grade/{id}", async (IDbContext repo, int id) =>
{
    Grade gradeToDelete = await repo.GetGradeById(id);
    if (gradeToDelete == null)
    {
        return Results.NotFound();
    }
    await repo.DeleteGrade(id);
    return Results.Ok();
});
#endregion

app.UseSwagger();
app.UseSwaggerUI(options =>
{
    options.SwaggerEndpoint("/swagger/v1/swagger.json", "v1");
    options.RoutePrefix = string.Empty;
});

app.Run();
