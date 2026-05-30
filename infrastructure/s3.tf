# ============================================================
#  S3 para PRUEBAS (configuración económica, NO producción)
# ============================================================

terraform {
  required_version = ">= 1.3"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# ------------------------------------------------------------
#  Variables
# ------------------------------------------------------------
variable "region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1" # us-east-1 suele ser la más barata
}

variable "bucket_name" {
  description = "Nombre del bucket (debe ser único a nivel global)"
  type        = string
  default     = "adri-kids-test-bucket"
}

variable "environment" {
  description = "Etiqueta de entorno"
  type        = string
  default     = "test"
}

# ------------------------------------------------------------
#  Provider
# ------------------------------------------------------------
provider "aws" {
  region = var.region
}

# Sufijo aleatorio para evitar choques de nombre (los nombres de
# bucket son únicos a nivel mundial)
resource "random_id" "suffix" {
  byte_length = 4
}

# ------------------------------------------------------------
#  Bucket S3
# ------------------------------------------------------------
resource "aws_s3_bucket" "test" {
  bucket = "${var.bucket_name}-${random_id.suffix.hex}"

  # IMPORTANTE para pruebas: permite que 'terraform destroy' borre
  # el bucket aunque tenga objetos dentro. NO usar en producción.
  force_destroy = true

  tags = {
    Name        = var.bucket_name
    Environment = var.environment
    ManagedBy   = "terraform"
  }
}

# Bloquea todo acceso público (buena práctica, además es gratis)
resource "aws_s3_bucket_public_access_block" "test" {
  bucket = aws_s3_bucket.test.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

# Cifrado en reposo con SSE-S3 (AES256). Es gratuito; KMS sí cuesta,
# por eso NO usamos KMS aquí.
resource "aws_s3_bucket_server_side_encryption_configuration" "test" {
  bucket = aws_s3_bucket.test.id

  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

# Versionado DESACTIVADO: cada versión guardada cuesta almacenamiento.
# Para pruebas no lo necesitas.
resource "aws_s3_bucket_versioning" "test" {
  bucket = aws_s3_bucket.test.id

  versioning_configuration {
    status = "Disabled"
  }
}

# Regla de ciclo de vida: borra objetos automáticamente a los 7 días
# para que el bucket de pruebas no acumule costos por descuido.
resource "aws_s3_bucket_lifecycle_configuration" "test" {
  bucket = aws_s3_bucket.test.id

  rule {
    id     = "expira-objetos-de-prueba"
    status = "Enabled"

    filter {} # aplica a todos los objetos

    expiration {
      days = 7
    }

    # Limpia subidas multipart incompletas (costo silencioso típico)
    abort_incomplete_multipart_upload {
      days_after_initiation = 1
    }
  }
}

# ------------------------------------------------------------
#  Outputs
# ------------------------------------------------------------
output "bucket_name" {
  description = "Nombre real del bucket creado"
  value       = aws_s3_bucket.test.id
}

output "bucket_arn" {
  description = "ARN del bucket"
  value       = aws_s3_bucket.test.arn
}
