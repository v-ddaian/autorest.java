﻿using AutoRest.Core.Utilities;
using System.Collections.Generic;
using System.Linq;

namespace AutoRest.Java.DanModel
{
    public class JavaModel : JavaFileGenerator
    {
        public JavaModel(IEnumerable<string> imports, string classComment, IEnumerable<string> classAnnotations, string className, IEnumerable<JavaMemberVariable> memberVariables)
        {
            Imports = imports;
            ClassComment = classComment;
            ClassAnnotations = classAnnotations;
            ClassName = className;
            MemberVariables = memberVariables;
        }

        protected IEnumerable<string> Imports { get; }

        protected string ClassComment { get; }

        protected IEnumerable<string> ClassAnnotations { get; }

        protected string ClassName { get; }

        protected IEnumerable<JavaMemberVariable> MemberVariables { get; }

        protected override string FileNameWithoutExtension => ClassName;

        public override JavaFile GenerateJavaFile(string folderPath, string headerComment, string package, int maximumCommentWidth)
        {
            return GenerateJavaFileWithHeaderAndPackage(folderPath, headerComment, package, maximumCommentWidth)
                .Import(Imports)
                .MultipleLineComment((comment) =>
                {
                    comment.SetWordWrapIndex(maximumCommentWidth)
                        .Line(ClassComment)
                        .SetWordWrapIndex(null);
                })
                .Annotation(ClassAnnotations)
                .PublicClass(ClassName, (classBlock) =>
                {
                    if (MemberVariables != null && MemberVariables.Any())
                    {
                        foreach (JavaMemberVariable memberVariable in MemberVariables)
                        {
                            classBlock.MultipleLineComment((comment) =>
                                {
                                    comment.Line(memberVariable.Comment);
                                })
                                .Annotation(memberVariable.Annotation)
                                .Line($"private {memberVariable.Type.Name} {memberVariable.Name};")
                                .Line();
                        }

                        IEnumerable<JavaMemberVariable> finalMemberVariables = MemberVariables.Where((memberVariable) => memberVariable.Final);
                        if (finalMemberVariables.Any())
                        {
                            classBlock.Block($"public {ClassName}()", (constructor) =>
                                {
                                    foreach (JavaMemberVariable memberVariable in MemberVariables)
                                    {
                                        JavaType type = memberVariable.Type;
                                        if (!type.IsPrimitive)
                                        {
                                            classBlock.Line($"{memberVariable.Name} = new {type.Name}();");
                                        }
                                        else
                                        {
                                            classBlock.Line($"{memberVariable.Name} = {memberVariable.DefaultValue};");
                                        }
                                    }
                                })
                                .Line();
                        }

                        foreach (JavaMemberVariable memberVariable in MemberVariables)
                        {
                            string variableName = memberVariable.Name;
                            JavaType variableType = memberVariable.Type;
                            string variableTypeName = variableType.Name;

                            classBlock.MultipleLineComment((comment) =>
                                {
                                    comment.Line($"Get the {variableName} value.")
                                        .Line()
                                        .Return($"the {variableName} value");
                                })
                                .Block($"public {variableTypeName} {variableName}()", (methodBlock) =>
                                {
                                    methodBlock.Return($"this.{variableName}");
                                })
                                .Line();

                            if (!memberVariable.Final)
                            {
                                classBlock.MultipleLineComment((comment) =>
                                    {
                                        comment.Line($"Set the {variableName} value.")
                                            .Line()
                                            .Param(variableName, $"the {variableName} value to set")
                                            .Return($"the {ClassName} object itself.");
                                    })
                                    .Block($"public {ClassName} with{variableName.ToPascalCase()}({variableTypeName} {variableName})", (methodBlock) =>
                                    {
                                        methodBlock.Line($"this.{variableName} = {variableName};")
                                            .Return("this");
                                    })
                                    .Line();
                            }
                        }
                    }
                });
        }
    }
}
